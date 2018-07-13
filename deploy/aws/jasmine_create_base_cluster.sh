export KOPS_STATE_STORE=s3://jasmine-kops-store
./jasmine_cluster.sh create --public-key ~/Documents/AWS/pem/jasmine-kops-cluster/jasmine.pub
kops update cluster jasmine.cf --yes
