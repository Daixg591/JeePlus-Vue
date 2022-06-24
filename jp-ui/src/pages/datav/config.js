export default {
  COMPNAME: "avue-echart-",
  NAME: "list",
  DEAFNAME: 'item',
  password: 'avue_data'
}
const BASE_URL = process.env.NODE_ENV !== 'production' ? process.env.VUE_APP_BASE_API : process.env.VUE_APP_SERVER_URL
export const url = BASE_URL