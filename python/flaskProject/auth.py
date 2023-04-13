# auth.py

from flask import Blueprint, render_template, request, redirect

auth_bp = Blueprint('auth', __name__)


@auth_bp.route('/login')
def login():
    return render_template('login.html')

@auth_bp.route('/index', methods=['POST'])
def index():
    username = request.form.get("username")
    password = request.form.get("password")

    if(username=="123"):
        if(password=="123"):
            print("username: %s" % username)
            print("password: %s" % password)
            return render_template('index.html', username=username)
        else:
            return render_template("login.html")
    else:
        return render_template("login.html")


