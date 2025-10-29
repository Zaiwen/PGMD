import pandas as pd


def find_repeat(file_path):
    df = pd.read_excel(file_path)
    different_values= []
    for value in df['index']:
        flag = 0
        for value1 in df["Run"]:
            if value1 == value:
                flag=1
        if not flag:
            different_values.append(value)

        # 在这里对每个值进行操作
        # print(value)

    # different_values = pd.concat([df['Run'],df['index']]).unique().tolist()
    print(len(different_values))
    pass


if __name__ == '__main__':
    path = 'C:\\Users\\79430\Desktop\\test.xlsx'
    find_repeat(path)