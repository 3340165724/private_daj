# auth.py

from flask import Blueprint, render_template, request, redirect

auth_bp = Blueprint('auth', __name__)


@auth_bp.route('/login', methods=['GET', 'POST'])
def login():
    return render_template('login.html')
