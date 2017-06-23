mv assignment2/GameConfiguration.java .
mv assignment2/SecretCodeGenerator.java .

unzip -o sample_runs_for_students.zip

cp assignment2/*.java sample_runs_for_students/test_runs/configuration_test_fail/
cp assignment2/*.java sample_runs_for_students/test_runs/standard_test/
cp assignment2/*.java sample_runs_for_students/test_runs/two_game_history/

cd sample_runs_for_students/test_runs/

mv configuration_test_fail/*.java configuration_test_fail/assignment2/
mv standard_test/*.java standard_test/assignment2/
mv two_game_history/*.java two_game_history/assignment2/

cd configuration_test_fail/
javac assignment2/*.java
java assignment2.Driver < input.txt > output.txt
cd ..

cd standard_test/
javac assignment2/*.java
java assignment2.Driver < input.txt > output.txt
cd ..

cd two_game_history/
javac assignment2/*.java
java assignment2.Driver < input.txt > output.txt

mv ~/EE422C/2.MasterMindProject/*.java ~/EE422C/2.MasterMindProject/assignment2/
