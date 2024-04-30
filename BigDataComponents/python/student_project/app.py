from flask import Flask, render_template, request

from DatabaseHandler import query

app = Flask(__name__)


@app.route('/')
def hello_world():  # put application's code here
    return 'Hello World!'


@app.route('/login')
def login():
    return render_template('login.html')


@app.route('/index', methods=["post", "GET"])
def index():
    #
    username = request.form.get('username')
    password = request.form.get('password')


    user_list = query("select * from tb_student")
    print("bdfbsndxjcvn", user_list)

    for i in user_list:
        if username.__eq__(i[1]) and password.__eq__("123456"):
            return render_template("index.html", username=i[2], list=user_list)
        else:
            return render_template("login.html")
        # print("i==",i[1])



if __name__ == '__main__':
    app.run()
