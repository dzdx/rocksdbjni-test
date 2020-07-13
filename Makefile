build:
	mvn clean package

run:
	java -cp target/rockdb-test-1.0-SNAPSHOT-shaded.jar RocksDBTest
