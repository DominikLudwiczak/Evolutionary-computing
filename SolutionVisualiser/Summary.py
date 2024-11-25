import pandas as pd
import matplotlib.pyplot as plt
import os


def create_empty_map(
    path_ids: list[int], title: str, instance: str, assigment_number: int
):
    data = pd.read_csv(
        f"../evolutionary-computing/src/main/resources/{instance}.csv",
        sep=";",
        header=None,
    )
    data.columns = ["X", "Y", "COST"]

    sizes = [int(x) * 0.2 for x in data["COST"]]

    plt.figure(figsize=(10, 8))

    plt.scatter(data["X"], data["Y"], color="blue", marker="o", s=sizes, label="Points")

    path_ids.append(path_ids[0])
    path_x = data["X"].iloc[path_ids]
    path_y = data["Y"].iloc[path_ids]

    plt.plot(path_x, path_y, color="red", marker="o", label="Path", linewidth=2)

    plt.title(title)
    plt.xlabel("X Coordinate")
    plt.ylabel("Y Coordinate")
    plt.legend()

    plt.grid(False)
    plt.savefig(f"assignment{assigment_number}/" + title + ".png")


def main(assignment_number: int):
    data = pd.read_csv(
        f"../evolutionary-computing/outputs/{assignment_number}/output.csv"
    )

    for row in data.iterrows():
        solution = [int(x) for x in row[1]["Solution"][1:-1].split(", ")]
        instance = row[1]["Instance"]
        title = row[1]["Name"] + " - " + instance
        print(instance)
        create_empty_map(solution, title, instance, assignment_number)


if __name__ == "__main__":
    main(6)
