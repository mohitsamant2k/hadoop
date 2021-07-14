# Note the time program has started to run
start=`date +%s`

# Set the classpath to avoid compilation errors
export HADOOP_CLASSPATH=$(hadoop classpath)

# Remove classes and jar files from previous run (if they exist)
cd ~/transaction/
rm *.jar
rm -rf classes

# Delete directory from HDFS if it exists previously
hadoop fs -rm -r /transaction
# Create a new working directory in HDFS
hadoop fs -mkdir /transaction
hadoop fs -mkdir /transaction/input
echo "----------------------------------Created working directory in HDFS-------------------------------------------"

# Copy the input.txt into Hadoop Distributed File System
hadoop fs -put ~/transaction/input/input.txt /transaction/input
echo "--------------------------------------Copied input.txt into HDFS----------------------------------------------"

# Create a new directory to store the Java classes
mkdir ~/transaction/classes
echo "----------------------------------Created folder for Java classes---------------------------------------------"

# Compile the code to generate the classes
javac -classpath ${HADOOP_CLASSPATH} -d ~/transaction/classes ~/transaction/Transaction.java
echo "------------------------Successfully compiled the Java code and created classes-------------------------------"

# Create a jar file from the classes to run it in Hadoop
jar -cvf trans.jar -C classes/ .
echo "-------------------------------------Successfully created jar file--------------------------------------------"

# Run the jar file using Hadoop
echo "--------------------------------------Running jar file in hadoop----------------------------------------------"
hadoop jar trans.jar Transaction /transaction/input /transaction/output

# Concatenate the lines of output from the output directory in HDFS
echo "-----------------------------------------------Output----------------------------------------"
hadoop dfs -cat /transaction/output/*

end=`date +%s`
runtime=$((end-start))
echo "Total Time: $runtime seconds"