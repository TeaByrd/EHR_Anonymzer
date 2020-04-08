mvn install
mkdir csv_annotations
cd csv_annotations/
touch Adresse.csv
touch Geschlecht.csv
touch Organisation.csv
touch Person.csv
cd ../
java -jar target/EHR_anonymizer-1.0-SNAPSHOT.jar 
