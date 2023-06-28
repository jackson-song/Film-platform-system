<template>
  <div class="login-container">
    <div class="login-img">
      <h3>阅读评分推荐平台系统</h3>
      <div class="login-about">
        <div class="login-input">
          <p>找回密码</p>
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

            <el-form-item label="邮箱" prop="email">
              <el-input  v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="password_confirm">
              <el-input type="password" v-model="ruleForm.password_confirm" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button  type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
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
    // eslint-disable-next-line no-unused-vars
    const checkcode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else {
        if (this.ruleForm.password_confirm !== '') {
          this.$refs.ruleForm.validateField('code')
        }
        callback()
      }
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
        email: '',
        password: '',
        code: '',
        password_confirm: '',
        type: []
      },
      rules: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        password_confirm: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        // code: [
        //   {validator: checkcode, trigger: 'blur'}
        // ],
        email: [
          {validator: checkMail, trigger: 'blur'}
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个兴趣爱好', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post(
            '/users/confirmpwd',
            {
              email: this.ruleForm.email,
              pwd: this.ruleForm.password,
              confirmpwd: this.ruleForm.password_confirm
            }
          ).then(response => {
            console.log(response.data)
            return response.data
          }, error => {
            console.log(error)
          })
          alert('submit!')
          Message.success('成功修改密码')
          this.$router.push('/')
        } else {
          Message.error('失败修改')
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
<style scoped>

</style>
