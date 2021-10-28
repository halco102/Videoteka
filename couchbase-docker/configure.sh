
set -m

/entrypoint.sh couchbase-server &

echo test

IPADDRESS=$(awk 'END{print $1}' /etc/hosts)

echo $IPADDRESS
echo $PORT

echo end

sleep 40

curl -v -X POST $IPADDRESS:8091/pools/default -d memoryQuota=512 -d indexMemoryQuota=512

echo "First command"
#sleep 15

curl -v $IPADDRESS:8091/node/controller/setupServices -d services=kv%2cn1ql%2Cindex
echo "Second command"
#sleep 15


curl -v $IPADDRESS:8091/settings/web -d port=$PORT -d username=$COUCHBASE_ADMINISTRATOR_USERNAME -d password=$COUCHBASE_ADMINISTRATOR_PASSWORD
echo "Set password and username"
#sleep 15

curl -i -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X POST $IPADDRESS:8091/settings/indexes -d 'storageMode=memory_optimized'


#sleep 15

couchbase-cli bucket-create -c $IPADDRESS:8091 -u $COUCHBASE_ADMINISTRATOR_USERNAME -p $COUCHBASE_ADMINISTRATOR_PASSWORD \
       --bucket=$COUCHBASE_BUCKET \
       --bucket-type=couchbase \
       --bucket-ramsize=200 \
       --enable-flush=1 \
       --enable-index-replica=1

sleep 15
echo "Create primary index on bucket"

curl -X POST -v -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD http://$IPADDRESS:8093/query/service -d statement=CREATE%20PRIMARY%20INDEX%20primary_index%20ON%20$COUCHBASE_BUCKET

echo $COUCHBASE_BUCKET

fg 1


