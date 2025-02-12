import "./App.css";
import CustomerList from "./Components/CustomerList";
import OrderForm from "./Components/OrderFrom";
import OrderList from "./Components/OrderList";
import ProductList from "./Components/ProductList";

function App() {
  return (
    <>
      <header className="my-3 d-flex" style={{ justifyContent: "center" }}>
        <nav>
          <a href="#orders" className="mx-2">Orders</a>
          <a href="#products" className="mx-2">Products</a>
          <a href="#customers" className="mx-2">Customers</a>
          <a href="#create-order" className="mx-2">Create Order</a>
        </nav>
      </header>
      <section id="orders">
        <OrderList />
      </section>
      <section id="products">
        <ProductList />
      </section>
      <section id="customers">
        <CustomerList />
      </section>
      <section id="create-order">
        <OrderForm />
      </section>
    </>
  );
}

export default App;
