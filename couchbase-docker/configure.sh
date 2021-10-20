
set -m

/entrypoint.sh couchbase-server &

echo test

IPADDRESS=$(awk 'END{print $1}' /etc/hosts)

echo $IPADDRESS

echo end

sleep 15

curl -v -X POST $IPADDRESS:8091/pools/default -d memoryQuota=512 -d indexMemoryQuota=512

curl -v $IPADDRESS:8091/node/controller/setupServices -d services=kv%2cn1ql%2Cindex

curl -v $IPADDRESS:8091/settings/web -d port=3001 -d username=$COUCHBASE_ADMINISTRATOR_USERNAME -d password=$COUCHBASE_ADMINISTRATOR_PASSWORD

curl -i -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X POST $IPADDRESS:3001/settings/indexes -d 'storageMode=memory_optimized'

couchbase-cli bucket-create -c $IPADDRESS:3001 -u $COUCHBASE_ADMINISTRATOR_USERNAME -p $COUCHBASE_ADMINISTRATOR_PASSWORD \
       --bucket=$COUCHBASE_BUCKET \
       --bucket-type=couchbase \
       --bucket-ramsize=200 \
       --enable-flush=1 \
       --enable-index-replica=1

sleep 10

curl -X POST -v -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD http://$IPADDRESS:8093/query/service -d statement=CREATE%20PRIMARY%20INDEX%20primary_index%20ON%20$COUCHBASE_BUCKET

echo $COUCHBASE_BUCKET

sleep 5

fg 1


