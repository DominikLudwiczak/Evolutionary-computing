import pandas as pd
import matplotlib.pyplot as plt
import os


def create_empty_map(path_ids, title):
    
    data = pd.read_csv("../evolutionary-computing/src/main/resources/TSPA.csv", sep=';', header=None)

    data.columns = ['X', 'Y', 'COST']
    print(data.head())

    plt.figure(figsize=(10, 8))
    plt.scatter(data['X'], data['Y'], color='blue', marker='o', label='Points')

    path_x = data['X'].iloc[path_ids]
    path_y = data['Y'].iloc[path_ids]

    plt.plot(path_x, path_y, color='red', marker='o', label='Path', linewidth=2)

    plt.title(title)
    plt.xlabel('X Coordinate')
    plt.ylabel('Y Coordinate')
    plt.legend()

    plt.grid(False)
    plt.show()




def main():
    data = pd.read_csv("../evolutionary-computing/output.csv")
    
    for row in data.iterrows():
        solution =[int(x) for x in row[1]['Solution'][1:-1].split(', ')]
        title = row[1]['Name'] + " - " + row[1]['Instance']
        create_empty_map(solution, title)

if __name__ == "__main__":
    main()