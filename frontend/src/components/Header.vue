<template>
  <header data-bs-theme="dark">
    <div class="collapse text-bg-dark" id="navbarHeader" style="background-color: darkgrey">
      <div class="container">
        <div class="row">
          <div class="col-sm-4 py-4 ">
            <h4 class="text-white">사이트맵</h4>
            <ul class="list-unstyled">
              <li>
                <router-link to="/" class="text-white"> 메인 </router-link>
              </li>
              <li v-if="$store.state.account.id">
                <router-link to="/orders" class="text-white"> 주문내역 </router-link>
              </li>
              <li>
                <router-link to="/login" class="text-white" v-if="!$store.state.account.id"> 로그인</router-link>
                <a to="/login" class="text-white" @click="logout()" v-else> 로그아웃 </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand d-flex align-items-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none" stroke="currentColor"
             stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2"
             viewBox="0 0 24 24">
          <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path>
          <circle cx="12" cy="13" r="4"></circle>
        </svg>
        <strong>Gallery</strong>
        </router-link>
        <RouterLink to="/cart" class="cart btn" v-if="$store.state.account.id">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </RouterLink>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader"
                aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation"><span
            class="navbar-toggler-icon"></span></button>
      </div>
    </div>
  </header>
</template>

<script>
import router from "@/scripts/router";
import store from "@/scripts/store";
import axios from "axios";

export default {
  name: 'Header',

  setup() {
    const logout = () => {
      axios.post("/api/account/logout").then(()=>{
        store.commit('setAccount', 0)
        router.push({path: "/"})
      })

    }

    return {logout};
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
Header ul li a {
  cursor: pointer;
}

Header .navbar .cart {
  margin-left: auto;
  padding: 10px;
  color:white;
}
</style>
