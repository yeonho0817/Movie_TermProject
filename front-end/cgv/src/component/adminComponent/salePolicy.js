import React from 'react';
import SalePolicyItem from './salePolicyItem';
import styles from './salePolicy.module.css'

function SalePolicy(props) {
    const { salePolicyList } = props;


    return (
        <div className={styles.sale_policy_container}>
            {
                salePolicyList.map( (salePolicy) => (
                    <SalePolicyItem key={salePolicy.id} salePolicy={salePolicy} />
                ))
            }
        </div>
    );
}

export default SalePolicy;