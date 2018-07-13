package com.jasmine.jasmine_core.StreamFunctions.MapFunctions;

import com.jasmine.jasmine_core.Models.*;
import com.jasmine.jasmine_core.Utils.FlinkParameters;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import redis.clients.jedis.Jedis;
import scala.Tuple2;

public class JNMobileRouteToSemaphoreRouteMapFunction extends RichMapFunction<JNMobileRoute, JNSemaphoreRoute> {
    private static final long serialVersionUID = 1L;

    private Jedis jedis;

    @Override
    public void open(Configuration parameters) {
        ParameterTool parameterTool = FlinkParameters.getParameters();
        this.jedis = new Jedis(parameterTool.get("flink.redis.host", "localhost"), parameterTool.getInt("flink.redis.port", 6379));
        if (parameterTool.has("flink.redis.password"))
            this.jedis.auth(parameterTool.get("flink.redis.password", "redis"));
    }

    @Override
    public void close() {
        this.jedis.close();
    }

    @Override
    public JNSemaphoreRoute map(JNMobileRoute mobileRoute) {
        ParameterTool parameterTool = FlinkParameters.getParameters();

        Double topLeftLatitude = parameterTool.getDouble("grid.top.left.latitude", 41.959598);
        Double topLeftLongitude = parameterTool.getDouble("grid.top.left.longitude", 12.389655);
        Double bottomRightLatitude = parameterTool.getDouble("grid.bottom.right.latitude", 41.836425);
        Double bottomRightLongitude = parameterTool.getDouble("grid.bottom.right.longitude", 12.593245);
        Integer latitudeSections = parameterTool.getInt("grid.latitude.sections", 5);
        Integer longitudeSections = parameterTool.getInt("grid.longitude.sections", 5);

        JNCellsContainer container = new JNCellsContainer(new JNCoordinates(topLeftLatitude, topLeftLongitude), new JNCoordinates(bottomRightLatitude, bottomRightLongitude), latitudeSections, longitudeSections);

        JNSemaphoreRoute semaphoreRoute = new JNSemaphoreRoute();
        Tuple2<Tuple2<String, String>, Double> lastMatchingSemaphore = null;

        for (JNMobilePing mobilePing : mobileRoute) {
            Tuple2<Tuple2<String, String>, Double> matchingSemaphore = this.findClosestSemaphore(mobilePing, container);
            if (matchingSemaphore == null) continue;
            if (lastMatchingSemaphore != null && (matchingSemaphore._1._1.equals(lastMatchingSemaphore._1._1) && matchingSemaphore._1._2.equals(lastMatchingSemaphore._1._2)))
                if (matchingSemaphore._2 < lastMatchingSemaphore._2)
                    semaphoreRoute.remove(semaphoreRoute.size() - 1);
                else continue;
            lastMatchingSemaphore = matchingSemaphore;
            semaphoreRoute.add(new JNSemaphorePing(matchingSemaphore._1._1, matchingSemaphore._1._2, mobilePing.getSpeed(), mobilePing.getTimestamp()));
        }

        return semaphoreRoute;
    }

    //Crossroads id, Semaphore id, distance
    public Tuple2<Tuple2<String, String>, Double> findClosestSemaphore(JNMobilePing mobilePing, JNCellsContainer container) {
        JNCell cell;
        try {
            String cellId = getBelongingCellId(mobilePing.getCoordinates(), container);
            String cellString = jedis.hget("cells", cellId);
            if (cellString == null) return null;
            cell = JNCell.FromJSONStringE(cellString, JNCell.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Tuple2<Tuple2<String, String>, Double> closestSemaphore = null;
        for (JNBaseSemaphore baseSemaphore : cell.getSemaphores()) {
            double distance = mobilePing.getCoordinates().distance(baseSemaphore.getPosition());
            if (closestSemaphore == null || distance < closestSemaphore._2)
                closestSemaphore = new Tuple2<>(new Tuple2<>(baseSemaphore.getCrossroadsId(), baseSemaphore.getSemaphoreId()), distance);
        }

        return closestSemaphore;
    }

    public String getBelongingCellId(JNCoordinates coordinates, JNCellsContainer container) {
        Tuple2<Integer, Integer> cellIndices = container.getBelongingCellIndices(coordinates);

        //concatenation of x and y: result should be kind of "6-9"
        return cellIndices._1 + "-" + cellIndices._2;

        //if needs an integer: result should be kind of "12"
        //return String.valueOf(cellIndices._2 * container.getXSize() + cellIndices._1);
    }

}
