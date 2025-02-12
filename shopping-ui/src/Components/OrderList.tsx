import React, { useEffect, useState } from "react";
import OrderService from "../api/services/AppService";
import { Order, Product } from "../types";
import "bootstrap/dist/css/bootstrap.min.css";

const OrderList: React.FC = () => {
  const [orders, setOrders] = useState<Order[]>([]);

  const fetchData = async () => {
    try {
      const orderResult: Order[] = await OrderService.getOrders();
      console.log(orderResult);
      setOrders(orderResult);
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
      {/* order list */}
      <div className="my-3 d-flex" style={{ justifyContent: "center" }}>
        <h2>Order List</h2>
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
              <th>Product ID</th>
              <th>Quantity</th>
              <th>Customer ID</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((o) => (
              <tr key={o.id}>
                <td>{o.id}</td>
                <td>{o.productId}</td>
                <td>{o.quantity}</td>
                <td>{o.customerId}</td>
                <td>{o.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default OrderList;
