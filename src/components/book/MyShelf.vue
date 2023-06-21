<template>
  <div>
  <h2>我的书架</h2>
  <el-button type="primary" size="small" @click="openNestedDialog2">删除书架信息</el-button>
  <el-dialog title="删除书架信息" :visible.sync="dialogFormVisible2">
    <el-form :model="form2">
      <el-form-item label="isbn" :label-width="formLabelWidth" prop="isbn">
        <el-input v-model="form2.isbn" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible2 = false">取 消</el-button>
      <el-button type="primary" @click="dialogFormVisible2 = false;deleteShelf()">确 定</el-button>
    </div>
  </el-dialog>
  <el-table :data="tableDataTest">
    <el-table-column prop="imgurl"  label="封面" width="150">
      <template   slot-scope="scope">
        <img referrerpolicy="no-referrer"  :src="scope.row.imgurl"  min-width="170" height="170" :alt=scope.row.imgurl :title=scope.row.imgurl />
      </template>
    </el-table-column>
    <el-table-column prop="bookname" label="书名" width="80">
    </el-table-column>
    <el-table-column prop="isbn" label="ISBN" width="80">
    </el-table-column>
    <el-table-column prop="author" label="作者" width="80">
    </el-table-column>
    <el-table-column prop="press" label="出版社" width="80">
    </el-table-column>
    <el-table-column prop="publicationtime" label="出版时间" width="80">
    </el-table-column>
    <el-table-column prop="bookgrading" label="评分" width="80">
    </el-table-column>
  </el-table>
  </div>
</template>
<script>
// import axios from 'axios'

import axios from 'axios'

export default {
  name: 'WebIndex',
  data () {
    return {
      tableDataName: [],
      tableDataTest: [],
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      dialogFormVisible: false,
      dialogVisible1: false,
      dialogFormVisible2: false,
      form: {
        password: '',
        newpassword: '',
        confirmpassword: ''
      },
      form2: {
        isbn: ''
      },
      form1: {
        email: '',
        username: '',
        gender: '',
        age: ''
      },
      formLabelWidth: '120px'
    }
  },
  created () {
    this.searchUser()
    this.shelfBook()
    // this.updatePassword()
  },
  methods: {
    deleteShelf () {
      axios.delete('http://localhost:3000/books/shelf/' + this.user.userid + '/' + this.form2.isbn

      ).then(response => {
        return response.data
      })
      this.pushToIndex()
    },
    shelfBook () {
      // console.log(this.user.userid)
      /*       axios.get('http://localhost:3000/books/shelf/' + this.user.userid
      ).then(res => {
        // console.log(res.data[0].books)
        // console.log(res.data[0])
        this.tableDataTest = res.data[0].books[0]
      }) */
      fetch('http://localhost:3000/books/shelf/' + this.user.userid)
        .then(res => res.json()).then(res => {
        // let x = ''
        // for (let i = 0; i < res.data.length; i++) {
        //   x += res.data[i].books
        // }
        // console.log(x)
          this.tableDataTest = res.data
        })
    },
    openNestedDialog1 () {
      this.dialogVisible1 = true
    },
    openNestedDialog2 () {
      this.dialogFormVisible2 = true
    },
    pushToIndex () {
      window.location.reload()
      // this.$router.push('/index')
    },
    updatemessage () {
      axios.post('http://localhost:3000/users/upuser',
        {
          'email': this.user.email,
          'username': this.form1.username,
          'gender': this.form1.gender,
          'age': this.form1.age
        }).then(response => {
        return response.data
      })
      this.pushToIndex()
    },
    searchUser () {
      // + this.user.email
      fetch('http://localhost:3000/users/get?email=' + this.user.email)
        .then(res => res.json()).then(res => {
          console.log(this.user.email)
          console.log(res)
          // console.log(Object.values(res.data))
          this.tableDataName = [res.data]
        // this.tableDataName = Object.values(res.data)
        // this.tableDataName[0][0] = res.data.userid
        // console.log(res.data.userid)
        // this.tableDataName = res.data
        // this.total = res.data.total
        })
    },
    updatePassword () {
      // function update () {
      //   axios.post(
      //     'http://localhost:3000/users/up',
      //     {
      //       'email': this.user.email,
      //       'pwd': this.user.pwd,
      //       'password': this.form.password,
      //       'newpassword': this.form.newpassword,
      //       'confirmedPassword': this.form.confirmpassword
      //     }).then(response => {
      //     console.log(this.user.email)
      //     console.log(this.user.pwd)
      //     console.log(this.form.password)
      //     console.log(this.form.newpassword)
      //     console.log(this.form.confirmpassword)
      //     return response.data
      //   })
      // }
      // this.dialogFormVisible = true
      axios.post(
        'http://localhost:3000/users/up',
        {
          'email': this.user.email,
          'pwd': this.user.pwd,
          'password': this.form.password,
          'newpassword': this.form.newpassword,
          'confirmedPassword': this.form.confirmpassword
        }).then(response => {
        console.log(this.user.email)
        console.log(this.user.pwd)
        console.log(this.form.password)
        console.log(this.form.newpassword)
        console.log(this.form.confirmpassword)
        // console.log(response.code)
        this.$confirm('确定修改密码吗？', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
          center: true
        }).then(() => {
          // update()
          this.$message({
            type: 'success',
            message: '修改密码成功,请退出登录'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消返回'
          })
        })
        return response.data
      })
    }
  }
}

</script>

<style scoped>

</style>
