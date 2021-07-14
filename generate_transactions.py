import random

# Open the input file
f = open("./input/input.txt", "w+")

# Set the total number of transactions to be generated (10 million)
N = int(1e7)
# Array to store the number of occurences in each class
true_counts = [0 for i in range(5)]

for i in range(1, N + 1):
    # Print a confirmation every 1 million transactions
    if i % 1000000 == 0:
        print(i//1000000, "million transactions generated")

    # No. of items
    nums = random.randint(1, 50)
    # Generate prices
    prices = [random.randint(100, 5000) for i in range(nums)]
    # Add sum to corresponding class
    true_counts[(nums-1)//10] += sum(prices)
    # Add the transaction to input.txt
    f.write(f"{i}: " + ' '.join(list(map(lambda x: str(x), prices))) + "\n")

print()

# Print total counts for each class
for i in range(len(true_counts)):
    print(f"class{i+1}, {true_counts[i]}")

# Close the file
f.close()
