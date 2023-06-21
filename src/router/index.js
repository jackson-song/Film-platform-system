
import Vue from 'vue'
import Router from 'vue-router'
// import TheEndIndexUpdate from '../components/book/TheEndIndexUpdate.vue'
// import MyShelf from '../components/book/MyShelf.vue'
// import Book from '../store/module/book'

Vue.use(Router)

// import WebLogin from "@/views/WebLogin.vue";
// import WebIndex from "@/views/WebIndex.vue";
// import WebRegister from "@/views/WebRegister.vue";

// const WebLoginTest = () => import('../views/WebLoginTest.vue')
const WebLogin = () => import('../views/WebLogin.vue')
const WebIndex = () => import('../views/WebIndex.vue')
// const WebRegister = () => import('../views/WebRegister.vue')
const BaseHeader = () => import('../components/common/BaseHeader.vue')
const BookTag = () => import('../components/book/BookTag.vue')
const BookTagContent = () => import('../components/book/BookTagContent.vue')
const BookTagMoreContent = () => import('../components/book/BookTagMoreContent.vue')
// const BookTypeContent = () => import('../components/book/BookTypeContent.vue')
const MovieTimeTypeContent = () => import('../components/movie/MovieTimeTypeContent.vue')
const MovieTagContent = () => import('../components/movie/MovieTagContent.vue')
const MovieTag = () => import('../components/movie/MovieTag.vue')
const MusicTagContent = () => import('../components/music/MusicTagContent.vue')
const CityTag = () => import('../components/city/CityTag.vue')
const CityActivityContent = () => import('../components/city/CityActivityContent')
const BaseUpdateHeader = () => import('../components/common/BaseUpdateHeader.vue')
const BookTest = () => import('../components/book/BookTest.vue')
const Test = () => import('../components/book/Test.vue')
const Test2 = () => import('../components/book/TotalPage.vue')
const HotBook = () => import('../components/book/HotBook.vue')
const SimpleHeader = () => import('../components/common/SimpleHeader')
const WebRegisterTest = () => import('../views/WebRegisterTest.vue')
const PasswordIndex = () => import('../views/PasswordIndex.vue')
const Password = () => import('../views/Password.vue')
const Book = () => import('../components/book/BookInfo.vue')
const BookFinal = () => import('../components/book/TheEndIndexUpdate.vue')
const MyShelfTest = () => import('../components/book/MyShelf.vue')
const BookInfoUpdate = () => import('../components/book/BookInfoUpdate.vue')
const BookIndex = () => import('../components/book/TheEndIndex.vue')
export default new Router({
  routes: [
    /*     {
      path: '/',
      name: 'home',
      component: WebIndex
    }, */
    {
      path: '/login',
      name: 'login',
      component: WebLogin
    }, {
      path: '/findpassword',
      name: 'find',
      component: PasswordIndex
    }, {
      path: '/find',
      name: 'findword',
      component: Password
    },
    {
      path: '/index',
      name: 'index',
      components: {
        default: SimpleHeader,
        content: WebIndex
      }
    }, {
      path: '/register',
      name: 'register',
      component: WebRegisterTest
      // component: WebRegisterTest
    },
    // {
    //   path: '/registertest',
    //   name: 'registertest',
    //   component: WebRegisterTest
    // },
    {
      path: '/',
      redirect: '/book-index-first'
    },
    {
      path: '/book-tag',
      name: 'bookTag',
      components: {
        default: BaseHeader,
        aside: BookTag,
        // content: BookTagContent
        content: BookTest
      }
    },
    {
      path: '/book-tag-update',
      name: 'bookTagTest',
      components: {
        default: BaseUpdateHeader,
        aside: BookTag,
        content: BookTagContent
      }
    },
    {
      path: '/book-tag-more-info',
      name: 'bookTagMoreInfo',
      components: {
        default: BaseHeader,
        aside: BookTag,
        content: BookTagMoreContent
      }
    },
    {
      path: '/book-hot',
      name: 'bookType',
      components: {
        default: BaseUpdateHeader,
        // content: BookTypeContent
        content: HotBook
      }
    },
    {
      path: '/movie-show-time',
      name: 'movieShowTime',
      components: {
        default: BaseHeader,
        content: MovieTimeTypeContent
      }
    },
    {
      path: '/movie-tag',
      name: 'movieTag',
      components: {
        default: BaseHeader,
        aside: MovieTag,
        content: MovieTagContent
      }
    },
    {
      path: '/music-tag',
      name: 'musicTag',
      components: {
        default: BaseHeader,
        content: MusicTagContent
      }
    },
    {
      path: '/city',
      name: 'city',
      components: {
        default: BaseHeader,
        aside: CityTag,
        content: CityActivityContent
      }
    }, {
      path: '/test',
      name: 'test',
      component: Test
    }, {
      path: '/book-final',
      name: 'test2',
      components: {
        default: BaseHeader,
        content: Test2
      }
    }, {
      path: '/book-final-update',
      name: 'book-final-update',
      components: {
        default: BaseUpdateHeader,
        content: Test2
      }
    },
    {
      path: '/book-index',
      name: 'book-index',
      components: {
        default: BaseUpdateHeader,
        content: BookFinal
      }
    }, {
      path: '/MyShelf',
      name: 'MyShelf',
      components: {
        default: BaseUpdateHeader,
        content: MyShelfTest
      }
    },
    {
      path: '/book-update',
      name: 'book-update',
      components: {
        default: BaseUpdateHeader,
        content: BookInfoUpdate
      }
    },
    {
      path: '/book-index-first',
      name: 'bookindexfirst',
      components: {
        default: BaseHeader,
        content: BookIndex
      }
    }, {
      path: '/book-index-login',
      name: 'book-index-login',
      components: {
        default: BaseUpdateHeader,
        content: BookIndex
      }
    },
    {
      path: '/book',
      // path: '/book/:imgurl/:bookname/:isbn/:author/:press/:publicationtime/:bookgrading/:synopsis',
      name: 'book',
      components: {
        default: BaseUpdateHeader,
        content: Book
      }
    }
  ]
})
