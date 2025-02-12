import React, { useEffect, useState } from "react";
import OrderService from "../api/services/AppService";
import { Customer } from "../types";
import "bootstrap/dist/css/bootstrap.min.css";

const CustomerList: React.FC = () => {
  const [customers, setCustomers] = useState<Customer[]>([]);

  const fetchData = async () => {
    try {
      const result: Customer[] = await OrderService.getCustomers();
      console.log(result);
      setCustomers(result);
    } catch (err) {
      console.log(err);
    } finally {
      console.log("Finally");
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      {/* Customer list */}

      <div className="my-3 d-flex" style={{ justifyContent: "center" }}>
        <h2>Customer List</h2>
      </div>
      <div className="my-3 d-flex" style={{ justifyContent: "center" }}>
        <button className="btn btn-primary" onClick={fetchData}>
          Refresh
        </button>
      </div>
      <div className="my-3 d-flex" style={{ justifyContent: "center" }}>
        <table className="table table-bordered table-striped mx-5">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Address</th>
              <th>City</th>
              <th>BalanceAmount</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((o) => (
              <tr key={o.id}>
                <td>{o.id}</td>
                <td>{o.name}</td>
                <td>{o.email}</td>
                <td>{o.phone}</td>
                <td>{o.address}</td>
                <td>{o.city}</td>
                <td>{o.balanceAmount}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default CustomerList;
