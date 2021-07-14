The following are the steps to run the code correctly -

1. Start Hadoop DFS and Yarn on the system.

2. Run the file generate_transactions.py using the command -

        python generate_transactions.py

   It will take approximately 5-10 minutes. This will
   create input.txt and generate 10 million random transactions.
   Once done, it will output the sum of transaction amounts that belong to each class.

3. Now run the run_hadoop.sh file.

4. The output from Step 2 and Step 3 will match, proving that the code
   is correctly segregating the transactions.
