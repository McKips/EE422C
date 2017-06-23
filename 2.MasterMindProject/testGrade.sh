mv assignment2/GameConfiguration.java .
mv assignment2/SecretCodeGenerator.java .

zip -r Project2_kt23347.zip assignment2
cp Project2_kt23347.zip grading/submissions
mv *.java assignment2/
cd grading
./grade.sh

cd detailed_feedback/
unzip -o Project2_kt23347.zip
cd ..
vim -O brief_results.csv detailed_feedback/Project2_kt23347/output_diff.txt
