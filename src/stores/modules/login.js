import { defineStore } from "pinia";
import { getToken } from "../../utils/auth";
import {store} from "../index";
export const useLoginDialogStore = defineStore("login", {
  state: () => ({
    isShow: false,
    isLogin: getToken() ? true : false,
  }),
  actions: {
    openLoginDialog() {
      this.isShow = true;
    },
    closeLoginDialog() {
      this.isShow = false;
    },
  },
});

export function useLoginDialogStoreWithOut() {
  return useLoginDialogStore(store);
}

