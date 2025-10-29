import {defineStore} from 'pinia'
import {store} from "../index";


export const useRegisterDialogStore = defineStore('register',{
    state:()=>({
        isShow:false
    }),
    actions:{
        openRegisterDialog(){
            this.isShow = true
        },
        closeRegisterDialog(){
            this.isShow = false
        }
    }
})

export function useRegisterDialogStoreWithOut(){
    return useRegisterDialogStore(store)
}