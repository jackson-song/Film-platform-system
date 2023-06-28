<template>
  <div class="login-container">
    <div class="login-img">
      <h3>阅读评分推荐平台系统</h3>
      <div class="login-about">
        <div class="login-input">
          <p>用户登录</p>
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                   class="demo-ruleForm">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pwd">
              <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="password_confirm">
              <el-input type="password" v-model="ruleForm.password_confirm" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
<!--              <el-button @click="resetForm('ruleForm')">重置</el-button>-->
              <el-button @click="gorouter">找回密码</el-button>
            </el-form-item>

          </el-form>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
import axios from 'axios'
import {Message} from 'element-ui'

export default {
  data () {
    const checkMail = (rule, value, callback) => {
      const reg = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z]{2,4})+$/
      if (!value) {
        return callback(new Error('邮箱不能为空'))
      } else if (reg.test(value)) {
        callback()
      } else {
        callback(new Error('不正确的邮箱'))
      }
    }
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('邮箱不能为空'))
      }
      setTimeout(() => {
        // !Number.isInteger(value)
        let choice = value instanceof String
        if (choice) {
          callback(new Error('请输入正确的邮箱'))
        } else {
          if (value.length > 7 && value.length < 20) {
            callback(new Error('不正确的邮箱格式'))
          } else {
            callback(new Error('不正确的邮箱格式'))
          }
        }
      }, 1000)
    }
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.password_confirm !== '') {
          this.$refs.ruleForm.validateField('password_confirm')
        }
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        pwd: '',
        password_confirm: '',
        // age: '',
        email: ''
      },
      rules: {
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        password_confirm: [
          {validator: validatePass2, trigger: 'blur'}
        ],
        age: [
          {validator: checkAge, trigger: 'blur'}
        ],
        email: [
          {validator: checkMail, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post(
            '/users/login',
            {
              email: this.ruleForm.email,
              pwd: this.ruleForm.password
              // password_confirm: this.ruleForm.password_confirm
            }).then(res => res.data).then(res => {
            console.log(res)
            if (res.code === '200') {
              // 存储
              sessionStorage.setItem('CurUser', JSON.stringify(res.data))
              // // console.log(res.data.menu)
              // this.$store.commit('setMenu', res.data.menu)
              // 跳转到主页
              // window.sessionStorage.setItem('isLogin', 'true')
              // this.$store.dispatch('asyncUpdateUser', {email: this.ruleForm.email})
              Message.success('登录成功')
              this.$router.replace('/book-final-update')
            } else {
              this.confirm_disabled = false
              Message.error('校验失败，邮箱或密码错误！')
              return false
            }
          })
        } else {
          this.confirm_disabled = false
          console.log('校验失败')
          return false
        }
      })
    },
    gorouter () {
      this.$router.replace('/findpassword')
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
<style scoped>

</style>
