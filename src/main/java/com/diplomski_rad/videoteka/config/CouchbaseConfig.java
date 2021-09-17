package com.diplomski_rad.videoteka.config;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.bucket.BucketSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${spring.couchbase.connection-string}")
    private String connectionString;

    @Value("${spring.couchbase.username}")
    private String userName;

    @Value("${spring.couchbase.password}")
    private String password;

    @Value("${spring.data.couchbase.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        Cluster cluster = Cluster.connect(connectionString, userName, password);
        Set<String> bucketNames = cluster.buckets().getAllBuckets().keySet();
        if (!bucketNames.contains(bucketName)) {
            System.out.println("Creating new bucket: " + bucketName);
            BucketSettings bucketSettings = BucketSettings.create(bucketName);
            cluster.buckets().createBucket(bucketSettings);
            cluster.queryIndexes().createPrimaryIndex(bucketName);
        }
    }

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

}
