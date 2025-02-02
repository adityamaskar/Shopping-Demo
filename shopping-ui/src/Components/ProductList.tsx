import React, { useEffect, useState } from "react";
import OrderService from "../api/services/AppService";
import { Product } from "../types";
import "bootstrap/dist/css/bootstrap.min.css";

const ProductList: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const result: Product[] = await OrderService.getProducts();
        console.log(result);
        setProducts(result);
      } catch (err) {
        console.log(err);
      } finally {
        console.log("Finally");
      }
    };
    fetchData();
  }, []);

  return (
    <>
      {/* product list */}

      <div className="my-3 d-flex" style={{ justifyContent: "center" }}>
        <h2>Product List</h2>
      </div>
      <div className="my-3 d-flex" style={{ justifyContent: "center" }}>
        <table className="table table-bordered table-striped mx-5">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Description</th>
              <th>Price</th>
              <th>Quantity In Stock</th>
            </tr>
          </thead>
          <tbody>
            {products.map((o) => (
              <tr key={o.id}>
                <td>{o.id}</td>
                <td>{o.name}</td>
                <td>{o.description}</td>
                <td>{o.price}</td>
                <td>{o.quantityInStock}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default ProductList;
