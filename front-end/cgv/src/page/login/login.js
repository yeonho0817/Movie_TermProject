import React, { useState } from 'react';
import styles from './login.module.css';
import { TextField } from '@mui/material';
import axios from 'axios';

import { Button } from 'react-bootstrap';
import Header from '../../component/homeComponent/header';
import { useNavigate } from 'react-router-dom';

export function isLogin() {
    if (sessionStorage.getItem("memberId")) return true;
    else return false;
}

const Login = () => {
    const navigate = useNavigate();

    const [userId, setUserId] = useState('');
    const [userPassword, setUserPassword] = useState('');

    const handleInputId = (e) => {
        // console.log(e.target.value);
        setUserId(e.target.value);
    }
    const handleInputPassword = (e) => {
        // console.log(e.target.value);
        setUserPassword(e.target.value);
    }

    const login = async () => {
        const data = {
            memberId:userId,
            memberPw:userPassword
        }
        await axios.post("http://localhost:8080/user/login", data).then((res) => {
            console.log(res.data);

            sessionStorage.setItem("memberId", res.data.data.id);
            sessionStorage.setItem("memberName", res.data.data.name);
            
            if (res.data.data.authority==="USER") 
            {
                // navigate("/");
                navigate(-1); // 예매에서 이전페이지로 이동하기 위해서 -1로 수정!
            } else {
                navigate("/admin")
            }

        }).catch((error) => {
            console.log(error)
            alert(error.response.data.message);

            setUserId("");
            setUserPassword("");

            document.querySelector("#id").value = "";
            document.querySelector("#pw").value = "";
        })
    }

    return (
        <>
            <Header/>
            <div className={styles.container}>
                <div className={styles.contents}>
                    <div className={styles.loginWrap}>
                        <div className={styles.loginBox}>
                            <div className={styles.loginInput}>
                                <TextField
                                    className={styles.inputBox}
                                    value={userId}
                                    onChange={handleInputId}
                                    helperText="Please enter your id"
                                    id="id"
                                    label="id"
                                />
                                <TextField
                                    className={styles.inputBox}
                                    value={userPassword}
                                    onChange={handleInputPassword}
                                    helperText="Please enter your password"
                                    id="pw"
                                    type="password"
                                    label="password"
                                />
                            </div>
                            <Button onClick={() => {login()}}>로그인</Button>
                        </div>
                    </div>
                </div>
                
            </div>
        </>
    );
};

export default Login;