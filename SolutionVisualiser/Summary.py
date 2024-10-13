import pandas as pd
import matplotlib.pyplot as plt
import os


def create_empty_map(path_ids):
    
    data = pd.read_csv("../evolutionary-computing/src/main/resources/TSPA.csv", sep=';', header=None)

    # Manually assign column names
    data.columns = ['X', 'Y', 'SomeOtherValue']  # Custom headers for the columns

    # Print the first few rows to verify
    print(data.head())

    # Create the plot
    plt.figure(figsize=(10, 8))
    plt.scatter(data['X'], data['Y'], color='blue', marker='o', label='Points')

    # Plot the path
    path_x = data['X'].iloc[path_ids]
    path_y = data['Y'].iloc[path_ids]

    # Plot the path as a line
    plt.plot(path_x, path_y, color='red', marker='o', label='Path', linewidth=2)

    # Add labels and title
    plt.title('Graph of X,Y Coordinates with Path')
    plt.xlabel('X Coordinate')
    plt.ylabel('Y Coordinate')
    plt.legend()

    # Show the plot
    plt.grid(True)
    plt.show()




def main():
    data = pd.read_csv("../evolutionary-computing/output.csv")
    print(data.head())
    Route = [int(x) for x in data['Solution'].iloc[0][1:-1].split(', ')]
    print(f'Route: {(Route)}')
    create_empty_map(Route)

if __name__ == "__main__":
    main()