<template>
  <div
    class="outer-content"
    :style="{background: currentModuleType.backgroundColor}"
  >
    <div class="inner-content">
      <h2
        class="search-logo"
        :class="currentModuleType.logo"
      >
        {{currentModuleType.text}}
      </h2>
</div>
    <div class="element-button">
    <div>
<!--      <el-button type="primary" size="small">退出</el-button>-->
      <el-dropdown>
        <span>返回</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
        <el-dropdown-menu slot="dropdown">

          <el-dropdown-item @click.native="logout">返回主页</el-dropdown-item>
<!--          <el-dropdown-item @click.native="register">注册</el-dropdown-item>-->
          <!--        <el-dropdown-item >个人中心</el-dropdown-item>-->
          <!--        <el-dropdown-item>退出登录</el-dropdown-item>-->
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
  </div>
</template>

<script>
import Test from '../book/Test.vue'
export default {
  name: 'BaseHeader',
  data () {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      keyword: '',
      isFocusOnInput: false,
      isFocusOnList: false,
      timer: 0,
      input: ''
    }
  },

  computed: {
    Test () {
      return Test
    },
    moduleTypes () {
      return this.$store.state.moduleTypes
    },
    currentModuleType () {
      return this.$store.state.currentModuleType
    },
    searchData () {
      return this.$store.state.searchData
    },
    showSearchSuggest () {
      // 当在搜索框中输入搜索内容时，会获取搜索结果，搜索结果的显示需满足几个条件：
      // 1、鼠标落在搜索输入框内或者鼠标落在搜索结果显示地容器上；
      // 2、搜索框里有内容
      // 3、返回的搜索结果不能为空
      return (this.isFocusOnInput || this.isFocusOnList) && this.keyword !== '' && this.searchData.length !== 0
    }
  },

  mounted () {
    let hashName = window.location.hash.replace(/#\/?/, '')
    this.onHashChange(hashName)
  },

  beforeRouteLeave (to, from, next) {
    this.keyword = ''
    this.$store.commit('SET_SEARCH_DATA', [])
    this.onHashChange(to.name)
    next()
  },

  methods: {
    logout () {
      this.$confirm('确定返回吗？', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
          type: 'success',
          message: '返回主页成功'
        })
        this.$router.push('/book-index-first')
        // sessionStorage.clear()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消返回'
        })
      })
    },
    login () {
      this.$router.push('/login')
    },
    register () {
      this.$router.push('/register')
    },
    handle () {
      this.$router.push('/index')
    },

    onHashChange (hashName) {
      // 监听hash值的变化，比如说从读书界面跳转到电影界面时，会因此调整搜索界面的背景颜色，文字等信息
      if (hashName.indexOf(this.currentModuleType.value) === -1) {
        for (let i = 0, ii = this.moduleTypes.length; i < ii; i++) {
          if (hashName.indexOf(this.moduleTypes[i].value) !== -1) {
            this.$store.commit('CHANGE_CURRENT_MODULE_TYPE', this.moduleTypes[i])
            return
          }
        }
      }
    },

    onInputFocus () {
      this.isFocusOnInput = true
    },

    onInputBlur () {
      this.isFocusOnInput = false
    },

    onListFocus () {
      this.isFocusOnList = true
    },

    onListBlur () {
      this.isFocusOnList = false
    }
  },

  watch: {
    keyword (newValue) {
      // 监听搜索框内文字的变化，根据搜索内容发送搜索请求
      // 设置延时，若用户在200ms内输入不同关键字搜索，会合并成一次请求，以最后一次为准
      if (newValue !== '' && this.currentModuleType.value !== 'city') {
        if (this.timer) {
          clearTimeout(this.timer)
        }
        this.timer = setTimeout(() => {
          this.timer = null
          this.$store.dispatch('getSearchData', {keyword: newValue})
        }, 200)
      }
    }
  },

  filters: {
    processedAuthor (value) {
      // 根据作者的信息字段
      if (value[0] && value[0].name) {
        let newValue = ''
        for (let i = 0, ii = value.length; i < ii; i++) {
          newValue += value[i].name + ' '
        }
        return newValue
      } else {
        return value.join()
      }
    }
  }
}
</script>

<style scoped>
.element-button{
  display:flex;justify-content:right;
}
.outer-content{
  width: 100%;
  min-width: 936px;
  margin-bottom: 40px;
  transition: background .5s ease-in-out;
}
.inner-content{

  width: 936px;
//height: 900px;
  margin: 0 auto;
  padding: 10px 0 5px;
}
.search-logo{
  width: 145px;
  height: 56px;
  display: inline-block;
  margin: 0 13px 0 0;
  color: transparent;
  background-position: 0 12px;
  background-repeat: no-repeat;
}
.book-logo{
  background-image: url("../../assets/book.png");
}
.movie-logo{
  background-image: url("../../assets/movie.png");
}
.music-logo{
  background-image: url("../../assets/music.png");
}
.city-logo{
  background-image: url("../../assets/city.png");
}
.search-target{
  display: inline-block;
}
.search-form{
  position: relative;
  top: 8px;
}
.search-input{
  width:350px;
  height:30px;
  border: none;
  padding-left: 10px;
  background: #fff;
  outline: none;
  box-shadow: 1px 1px 1px rgba(0,0,0,0.16);
}
.search-submit{
  width: 37px;
  height: 32px;
  margin: 1px 0 0 -4px;
  vertical-align: middle;
  border:none;
  color: transparent;
  cursor: pointer;
  outline: none;
  box-shadow: 1px 1px 1px rgba(0,0,0,0.16);
  background-repeat: no-repeat;
  background-position: 0 -40px;
}
.book-search-icon{
  background-image: url("../../assets/book_search_btn.png");
}
.movie-search-icon{
  background-image: url("../../assets/movie_search_btn.png");
}
.music-search-icon{
  background-image: url("../../assets/music_search_btn.png");
}
.city-search-icon{
  background-image: url("../../assets/city_search_btn.png");
}
.search-suggest{
  position: absolute;
  width: 360px;
  z-index: 99;
  margin-top: 6px;
  background: #fff;
  border: 1px solid #ddd;
}
.search-item{
  border-bottom: 1px solid #eee;
  overflow: hidden;
}
.search-item:hover{
  background: #F9F9F9;
}
.search-item-link{
  display: block;
  padding: 6px;
  overflow: hidden;
  zoom: 1;
  color: #999;
}
.search-item-image{
  width: 40px;
  height: 56px;
  float: left;
  margin-right: 8px;
  margin-top: 3px;
}
.search-item-title{
  display: inline-block;
  font-weight: normal;
  color: #369;
  font-size: 13px;
}
.search-item-author,
.search-item-genres{
  margin-top: 10px;
}
.module-list{
  display: inline-block;
  position: relative;
  top: 10px;
  left: 40px;
}
.module-list li{
  display: inline-block;
  margin-left: 25px;
  padding: 2px 5px;
  cursor: pointer;
}
.module-list li:hover{
  background: #37a;
  color: white;
}
.outer-more-module{
  display: none;
  position: absolute;
  margin-left: -40px;
}
.inner-more-module{
  width: 100px;
  margin-top: 10px;
  background: #fff;
  border: 1px solid #e7eaf1;
  box-shadow: 0 5px 20px rgba(0,34,77,.1);
}
.module-list li:hover .outer-more-module{
  display: block;
}
.inner-more-module::before{
  content: '';
  position: absolute;
  width: 0;
  height: 0;
  margin-top: -10px;
  margin-left: calc(50% - 10px);
  border-left: 10px solid transparent;
  border-right: 10px solid transparent;
  border-bottom: 10px solid white;
}
.more-module-link{
  display: block;
  padding: 10px;
  text-align: center;
  color: #000;
}
.more-module-link:hover{
  background: #F4F8FB;
}
</style>
