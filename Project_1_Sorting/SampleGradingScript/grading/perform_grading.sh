#!/bin/bash

TA_MODE=false

main_dir=$(pwd)
submissions_dir="$main_dir/submissions"
grading_project="$main_dir/grading_project"
detailed_feedback_dir="$main_dir/detailed_feedback"

collect_results()
{
	if [ ! -f "$main_dir/brief_results.csv" ]
	then
		echo '"submission_name", "grade", "comment"' > "$main_dir/brief_results.csv"
	fi

	mkdir -p "$detailed_feedback_dir"
	cd "$submissions_dir"
	for tested_submission in */
	do
		tested_submission=${tested_submission:0:${#tested_submission}-1}
		brief_result_line=$( python2.6 "$main_dir/helper_scripts/score_extractor.py" "$tested_submission/TEST-assignment1.SortToolsTest.xml" "$tested_submission" )
		echo "$brief_result_line" >> "$main_dir/brief_results.csv"
		zip -ry9Tm "$tested_submission" "$tested_submission" > /dev/null
		mv "$tested_submission".zip "$detailed_feedback_dir"

	done
	cd "$main_dir"
}

make_fresh_copy_of_grading_project()
{
    if [ "$TA_MODE" = true ]
    then
        if [ ! -d "/tmp/422-S17" ]
        then
	        this_dir=$(pwd)
            cd "/tmp"
            git clone "https://github.com/MoezGholami/422-S17"
	        cd "$this_dir"
        fi

        rm -rf "$grading_project"
        cp -R "/tmp/422-S17/assignment1_solution" "$grading_project"
    fi
}

perform_test_on_this_submission()
{
	this_dir=$(pwd)

    make_fresh_copy_of_grading_project

	rm -rf "$grading_project/src/main/java/assignment1/*"
    for java_file in $(find . -name "*.java")
    do
        if ! ( echo "$java_file" | grep -iq "test" )
        then
            mv "$java_file" "$grading_project/src/main/java/assignment1/"
        fi
    done

	cd "$grading_project"
    rm -rf target
	mvn test > /dev/null 2>&1 || echo " "

	cd "$this_dir"

	rm -rf *
	if [[ -f "$grading_project/target/surefire-reports/TEST-assignment1.SortToolsTest.xml" ]]
    then
    	mv "$grading_project/target/surefire-reports/TEST-assignment1.SortToolsTest.xml" .
    fi
}

run_test_on_submissions()
{
	cd "$submissions_dir"

    if ( ls *.java 1> /dev/null 2>&1 )
    then
	    for submission_raw_name in *.java
	    do
	    	submission_name="${submission_raw_name%%.*}"
	    	echo "### going on: " "$submission_name"

	    	mkdir "$submission_name"
            mv "$submission_raw_name" "$submission_name/SortTools.java"
            cd "$submission_name"

	    	perform_test_on_this_submission

	    	cd "$submissions_dir"
	    done
    fi

	cd "$main_dir"
}

prepare_maven_idempotent()
{
    if [ -f ~/.profile ]
    then
        . ~/.profile
    fi

    if ! type "mvn" > /dev/null 2>&1 # if maven is not loaded
    then
        module load maven
    fi
}

main()
{
	set -e

	rm -rf  "$detailed_feedback_dir" "$main_dir/brief_results.csv"

    prepare_maven_idempotent
	run_test_on_submissions
	echo "collecting results ..."
	collect_results
	echo "finished"

	set +e
}

main
