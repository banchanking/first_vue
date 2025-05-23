<template>
  <div class="Home">

    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, idx) in state.items" :key="idx">
            <Card :item="item"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import Card from "@/components/Card.vue";
import axios from "axios";
import {reactive} from "vue";
import Card from "@/components/Card.vue";

export default {
  name: "Home",
  components: {Card},
  setup() {
    const state = reactive({ // 반응형 데이터 생성 (object, array 이외에는 사용할 수 없다.)
      items: []
    })

    axios.get("/api/items").then((res) => {
      state.items = res.data;
    })

    return {state}
  }

}
</script>

<style scoped>

</style>