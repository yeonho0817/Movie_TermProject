import React, { useEffect, useState } from 'react';
import styles from './salePolicyItem.module.css'

import InputAdornment from '@mui/material/InputAdornment';
import Input from '@mui/material/Input';
import FormControl from '@mui/material/FormControl';
import Fab from '@mui/material/Fab';
import SaveOutlinedIcon from '@mui/icons-material/SaveOutlined';
import axios from 'axios';


function SalePolicyItem(props) {

    const { salePolicy } = props;

    const [flatValue, setFlatValue] = useState(null);
    const [flatRateValue, setFlatRateValue] = useState(null);



    const saveFlatDiscount = async () => {
        const data = {
            salePolicyId:salePolicy.salePolicyId,
            value: flatValue
        }

        await axios.put("http://localhost:8080/salePolicy/flat/update", data).then((res) => {
            console.log(res.data);
            alert(res.data.message);
        }).catch((error) => {
            const errorMessage = error.response.data.message;

            alert(errorMessage);

            setFlatValue(salePolicy.value);
            document.querySelector("#flatValue").value = salePolicy.value;
        })
    }

    const saveFlatRateDiscount = async () => {
        const data = {
            salePolicyId:salePolicy.salePolicyId,
            value: flatRateValue
        }

        await axios.put("http://localhost:8080/salePolicy/flatRate/update", data).then((res) => {
            console.log(res.data);
            alert(res.data.message);
        }).catch((error) => {
            const errorMessage = error.response.data.message;

            alert(errorMessage);

            setFlatRateValue(salePolicy.value);
            document.querySelector("#flatRateValue").value = salePolicy.value;
        })
    }


    return (
        <div className={styles.sale_policy_item_container}>
            
            <span style={{fontSize:"1.2rem"}}>{salePolicy.salePolicyName} : </span>
            
            {
            salePolicy.salePolicyName === "정액할인" ?

                <div style={{display:'inline-flex'}}>   
                    <FormControl variant="standard" sx={{ width: '15ch', margin: "0px 60px" }}>
                        <Input
                            id="flatValue"
                            defaultValue={salePolicy.value}
                            endAdornment={<InputAdornment position="end">원</InputAdornment>}
                            onChange={(e) => {
                                setFlatValue(e.target.value);
                            } }
                        />
                    </FormControl>
                    <Fab sx={{width:"100px"}} variant="extended" size="medium" color="primary" aria-label="add" onClick={() => {saveFlatDiscount();}}>
                            <SaveOutlinedIcon sx={{ mr: 1 }} />
                            저장
                    </Fab>
                </div>

            :
                <div style={{display:'inline-flex'}}>   
                    <FormControl variant="standard" sx={{ width: '15ch', margin: "0px 60px" }}>
                        <Input
                            id="flatRateValue"
                            defaultValue={salePolicy.value}
                            endAdornment={<InputAdornment position="end">/100 (%)</InputAdornment>}
                            onChange={(e) => {
                                setFlatRateValue(e.target.value);
                            } }
                        />
                    </FormControl>

                    <Fab sx={{width:"100px"}} variant="extended" size="medium" color="primary" aria-label="add" onClick={() => {saveFlatRateDiscount();}}>
                        <SaveOutlinedIcon sx={{ mr: 1 }} />
                        저장
                    </Fab>
                </div>
            }
            

            
            
        </div>
    );
}

export default SalePolicyItem;