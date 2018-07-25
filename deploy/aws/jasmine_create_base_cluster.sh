cd "$(dirname "$0")"

export KOPS_STATE_STORE=s3://jasmine-kops-store
./jasmine_cluster.sh create --public-key ~/Documents/Development/jasmine/other/jasmine.pem
kops update cluster jasmine.cf --yes
