import pandas as pd
import matplotlib.pyplot as plt

def create_similarity_plot(objectives: list[int], values: list[float], title: str, assignment_number: int, correlation: float = None):
    plt.figure(figsize=(10, 8))

    plt.scatter(objectives, values, label="Similarity")

    plt.title(title)
    plt.xlabel("Objective")
    plt.ylabel("Similarity")
    plt.legend()
    
    if correlation:
        plt.text(0.8, 0.9, f"Correlation: {correlation}", color="red", fontsize=12, transform=plt.gca().transAxes)

    plt.grid(False)
    plt.savefig(f"assignment{assignment_number}/" + title + ".png")

def main(assignment_number: int):
    data = pd.read_csv(
        f"../evolutionary-computing/outputs/{assignment_number}/output.csv"
    )

    objective = data[data["Name"] == "Objective"]
    objectives = {}
    for obj in objective.iterrows():
        values = [int(x) for x in obj[1]["Values"][1:-1].split(", ")]
        instance = obj[1]["Instance"]
        objectives[instance] = values
    
    for row in data.iterrows():
        if row[1]["Name"] == "Objective":
            continue
        values = [float(x) for x in row[1]["Values"][1:-1].split(", ")]
        instance = row[1]["Instance"]
        title = row[1]["Name"] + " - " + instance
        obj = objectives[instance]
        if len(obj) != len(values):
            obj = obj[1:]
        correlation = round(pd.Series(obj).corr(pd.Series(values)), 2)
        create_similarity_plot(obj, values, title, assignment_number, correlation)

if __name__ == "__main__":
    main(8)