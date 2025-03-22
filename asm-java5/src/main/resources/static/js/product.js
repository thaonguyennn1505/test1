import createToast, { toastComponent } from "./toast.js";
import { setTotalCartItemsQuantity } from "./header.js";

// STATIC DATA
const currentUserIdMetaTag = document.querySelector("meta[name='currentUserId']");
const productIdMetaTag = document.querySelector("meta[name='productId']");

const quantityInput = document.querySelector("#cart-item-quantity");
const productTitleElement = document.querySelector(".title");

// MESSAGES
const REQUIRED_SIGNIN_MESSAGE = "Vui lòng đăng nhập để thực hiện thao tác!";
const SUCCESS_ADD_CART_ITEM_MESSAGE = (quantity, productTitle) =>
  `Đã thêm thành công ${quantity} sản phẩm ${productTitle} vào giỏ hàng!`;
const FAILED_ADD_CART_ITEM_MESSAGE = "Đã có lỗi truy vấn!";

// UTILS
async function _fetchPostAddCartItem() {
  const cartItemRequest = {
    userId: currentUserIdMetaTag.content,
    productId: productIdMetaTag.content,
    quantity: quantityInput.value,
  };

  const response = await fetch("/cartItem", {
    method: "POST",
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(cartItemRequest),
  });
  return [response.status, await response.json()];
}


// EVENT HANDLERS
function noneSigninEvent() {
  createToast(toastComponent(REQUIRED_SIGNIN_MESSAGE));
}

async function buyNowBtnEvent() {
  const [status] = await _fetchPostAddCartItem();
  if (status === 200) {
    window.location.href = "/cart";
  } else if (status === 404) {
    createToast(toastComponent(FAILED_ADD_CART_ITEM_MESSAGE, "danger"));
  }
}

async function addCartItemBtnEvent() {
  const [status] = await _fetchPostAddCartItem();
  if (status === 200) {
    createToast(toastComponent(
      SUCCESS_ADD_CART_ITEM_MESSAGE(quantityInput.value, productTitleElement.innerText), "success"));
    setTotalCartItemsQuantity(quantityInput.value);
  } else if (status === 404) {
    createToast(toastComponent(FAILED_ADD_CART_ITEM_MESSAGE, "danger"));
  }
}

// MAIN
const buyNowBtn = document.querySelector("#buy-now");
const addCartItemBtn = document.querySelector("#add-cart-item");

if (currentUserIdMetaTag) {
  buyNowBtn.addEventListener("click", buyNowBtnEvent);
  addCartItemBtn.addEventListener("click", addCartItemBtnEvent);
} else {
  buyNowBtn.addEventListener("click", noneSigninEvent);
  addCartItemBtn.addEventListener("click", noneSigninEvent);
}
