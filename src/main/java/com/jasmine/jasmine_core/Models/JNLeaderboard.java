package com.jasmine.jasmine_core.Models;

import java.util.ArrayList;
import java.util.List;

public abstract class JNLeaderboard<T> {
    private List<T> leaderboard;
    private int maxSize;
    private ORDER order;

    public JNLeaderboard(int maxSize, ORDER order) {
        this.leaderboard = new ArrayList<>();
        this.maxSize = maxSize;
        this.order = order;
    }

    public boolean add(T o) {
        for (int i = 0; i < this.leaderboard.size(); i++)
            if (this.equals(this.leaderboard.get(i), o))
                if (this.compare(this.leaderboard.get(i), o) != 0)
                    this.leaderboard.remove(i);
                else return false;

        int order = this.order == ORDER.ASCENDING ? 1 : -1;

        for (int i = 0; i < this.leaderboard.size(); i++)
            if (this.compare(this.leaderboard.get(i), o) == order) {
                this.leaderboard.add(i, o);
                if (this.leaderboard.size() > this.maxSize)
                    this.leaderboard.remove(this.leaderboard.size() - 1);
                return true;
            }
        if (this.leaderboard.size() < this.maxSize) {
            this.leaderboard.add(o);
            return true;
        }
        return false;
    }

    public boolean merge(JNLeaderboard<T> leaderboard) {
        return this.mergeOutOfOrder(leaderboard);
    }

    public boolean mergeOrdered(JNLeaderboard<T> leaderboard) {
        if (this.leaderboard.size() == 0) {
            this.leaderboard.addAll(leaderboard.leaderboard);
            return true;
        }

        boolean result = false;

        for (T o : leaderboard.getLeaderboard())
            for (int i = 0; i < this.leaderboard.size(); i++)
                if (this.equals(this.leaderboard.get(i), o))
                    if (this.compare(this.leaderboard.get(i), o) != 0) {
                        this.leaderboard.remove(i);
                        result = true;
                    }

        int order = this.order == ORDER.ASCENDING ? 1 : -1;

        int i = 0;
        for (T o : leaderboard.getLeaderboard())
            for (; i < this.leaderboard.size(); i++)
                if (this.compare(this.leaderboard.get(i), o) == order) {
                    this.leaderboard.add(i, o);
                    if (this.leaderboard.size() > this.maxSize)
                        this.leaderboard.remove(this.leaderboard.size() - 1);
                    result = true;
                }

        return result;
    }

    public boolean mergeOutOfOrder(JNLeaderboard<T> leaderboard) {
        boolean result = false;
        for (T o : leaderboard.getLeaderboard())
            result |= this.add(o);
        return result;
    }

    protected boolean equals(T a, T b) {
        return a.equals(b);
    }

    protected abstract int compare(T a, T b); // return 1 if is bigger, -1 smaller, 0 equal

    public List<T> getLeaderboard() {
        return this.leaderboard;
    }

    /*
        Getter and Setter
     */

    public void setLeaderboard(List<T> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public ORDER getOrder() {
        return this.order;
    }

    public void setOrder(ORDER order) {
        this.order = order;
    }

    public enum ORDER {ASCENDING, DESCENDING}

}
