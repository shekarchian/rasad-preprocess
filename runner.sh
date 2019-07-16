for i in `seq 0 8`;
do
    java -jar target/link_prediction-1.0-SNAPSHOT.jar  $i >> log.txt
done    
