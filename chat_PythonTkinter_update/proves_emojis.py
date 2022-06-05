# Import the required libraries
from tkinter import *
from tkinter import ttk

# Create an instance of Tkinter Frame
win = Tk()

# Set the geometry of Tkinter Frame
win.geometry("700x250")

style=ttk.Style()
style.theme_use('vista')
style.configure("Vertical.TScrollbar", background="green", bordercolor="red", arrowcolor="white")

# Create a vertical scrollbar
scrollbar = ttk.Scrollbar(win, orient='vertical')
scrollbar.pack(side=RIGHT, fill=BOTH)

# Add a Text Widget
text = Text(win, width=15, height=15, wrap=CHAR,
yscrollcommand=scrollbar.set)

for i in range(1000):
   text.insert(END, i)

text.pack(side=TOP, fill=X)

# Configure the scrollbar
scrollbar.config(command=text.yview)

win.mainloop()