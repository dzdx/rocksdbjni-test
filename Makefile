run:
	mvn -q clean compile
	mvn -q exec:java -Dexec.mainClass="RocksDBTest"