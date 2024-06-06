

import os
import tkinter as tk
from tkinter import filedialog

from PGMDB.Microbe import temp_resolve_microbe, resolve_microbe, change_name, change_feed_name,extraction_metadata,process_lefse


def count_files(directory):
    """
       统计一个文件夹下面所有文件的数目
       """
    count = 0
    for root, dirs, files in os.walk(directory):
        count += len(files)
    return count

def my_function(file_path):
    # 在这里编写您的函数代码
    resolve_microbe(file_path)
    # temp_resolve_microbe(file_path)
    print("您选择的文件路径是：", file_path)

def my_function_v2(file_path):
    # 在这里编写您的函数代码
    change_name(file_path)
    # temp_resolve_microbe(file_path)
    print("您选择的文件路径是：", file_path)

def my_function_v3(file_path):
    # 在这里编写您的函数代码
    change_feed_name(file_path)
    # temp_resolve_microbe(file_path)
    print("您选择的文件路径是：", file_path)

# def my_function_v3(file_path):
#     # 在这里编写您的函数代码
#     extraction_metadata(file_path)
#     # temp_resolve_microbe(file_path)
#     print("您选择的文件路径是：", file_path)
def my_function_of_lefse(file_path):
    process_lefse(file_path)
    print("您选择的文件路径是：", file_path)


def create_gui():
    def select_directory():
        """选择文件夹"""
        directory = filedialog.askdirectory()
        if directory:
            count = count_files(directory)
            result_label.config(text=f"文件夹 {directory} 中的文件数为：{count}")

    def browse_file(file_path_entry):
        file_path = filedialog.askopenfilename()
        file_path_entry.delete(0, tk.END)
        file_path_entry.insert(0, file_path)

    def call_function(file_path_entry):
        file_path = file_path_entry.get()
        my_function(file_path)

    def change_function(file_path_entry):
        file_path = file_path_entry.get()
        my_function_v2(file_path)

    def change_feed_function(file_path_entry):
        file_path = file_path_entry.get()
        my_function_v3(file_path)

    # def extraction_function(file_path_entry):
    #     file_path = file_path_entry.get()
    #     my_function_v3(file_path)

    def lefse(file_path_entry):
        file_path = file_path_entry.get()
        my_function_of_lefse(file_path)

    #退出
    def quit_program(root):
        root.quit()
        root.destroy()

    # 创建主窗口
    root = tk.Tk()
        # 设置窗口大小
    root.geometry("400x300")

        # 设置窗口标题
    root.title("选择文件")
        # 设置窗口背景
    root.configure(bg="skyblue")

        # 创建文件路径输入框
    file_path_entry = tk.Entry(root, width=70)
    file_path_entry.pack()

        # 创建浏览按钮
    browse_button = tk.Button(root, text="浏览", command=lambda:browse_file(file_path_entry))
    browse_button.pack()

    # 创建改名按钮
    ok_button = tk.Button(root, text="修改文件名字", command=lambda:change_function(file_path_entry))
    ok_button.pack()

    # 创建改名按钮
    ok_button = tk.Button(root, text="修改饲料文件名字", command=lambda:change_feed_function(file_path_entry))
    ok_button.pack()

    #提取公共元数据
    # ok_button = tk.Button(root, text="提取元数据", command=lambda: extraction_function(file_path_entry))
    # ok_button.pack()

        # 处理lefse数据

    ok_button = tk.Button(root, text="处理lefse", command=lambda: lefse(file_path_entry))
    ok_button.pack()

    # 创建确定按钮
    ok_button = tk.Button(root, text="确定", command=lambda:call_function(file_path_entry))
    ok_button.pack()

        # 创建选择文件夹按钮
    select_button = tk.Button(root, text="选择文件夹", command=select_directory)
    select_button.pack(side="left", padx=10, pady=10)

        # 创建退出按钮
    quit_button = tk.Button(root, text="退出", command=lambda: quit_program(root))
    quit_button.pack()
        # 创建结果标签
    result_label = tk.Label(root, text="")
    result_label.pack(side="bottom", padx=10, pady=10)

    # 运行主循环
    root.mainloop()

if __name__ == '__main__':
    print("#程序开始运行----------")
    create_gui()
    print("#程序运行结束----------")