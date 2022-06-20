import axios from 'axios';
import React, { useState, useEffect } from 'react';
import AdminHeader from '../../component/adminComponent/adminHeader';
import SalePolicy from '../../component/adminComponent/salePolicy';
import ScreenSchedule from '../../component/adminComponent/screenSchedule';
import styles from './admin.module.css'




function Admin() {
    const [salePolicyList, setSalePolicyList] = useState([]);
    const [screenScheduleList, setScreenScheduleList] = useState([]);

    const getAdminData = async () => {
        await axios.get("http://localhost:8080/salePolicy").then((res) => {
            console.log(res.data);

            setSalePolicyList(res.data.data.readSalePolicyDTOList);
            setScreenScheduleList(res.data.data.readScreenScheduleInfoDTOList);

           
        });        
    }

    useEffect(() => {
        getAdminData();
    }, [])


    return (
        <div className={styles.admin_container}>
            

            <AdminHeader />
            <div className={styles.sale_policy_container}>
                <SalePolicy salePolicyList={salePolicyList} />
            </div>
            
            <hr style={{border:"2px solid black"}}/>
            
            <h3>상영 시간표</h3>
            <div className={styles.screenSchedlule_container}>
                <ScreenSchedule screenScheduleList={screenScheduleList} setScreenScheduleList={setScreenScheduleList}/>
            </div>
        </div>
    );
}

export default Admin;