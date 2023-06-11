<template>
  <div class="login-container">
    <div class="login-img">
      <h3>阅读评分推荐平台系统</h3>
      <div class="login-about">
        <div class="login-input">
          <p>用户注册</p>
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
            <el-form-item label="兴趣爱好" prop="type">
              <el-checkbox-group v-model="ruleForm.type">
                <el-checkbox label="言情" name="type"></el-checkbox>
                <el-checkbox label="奇幻" name="type"></el-checkbox>
                <el-checkbox label="武侠" name="type"></el-checkbox>
                <el-checkbox label="健康" name="type"></el-checkbox>
                <el-checkbox label="管理" name="type"></el-checkbox>
                <el-checkbox label="设计" name="type"></el-checkbox>
                <el-checkbox label="职场" name="type"></el-checkbox>
                <el-checkbox label="美食" name="type"></el-checkbox>
                <el-checkbox label="编程" name="type"></el-checkbox>
                <el-checkbox label="通信" name="type"></el-checkbox>
                <el-checkbox label="色情  " name="type"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>

            <el-form-item>
<!--              submitForm('ruleForm')-->
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
// eslint-disable-next-line no-unused-vars
import axios from 'axios'
// eslint-disable-next-line no-unused-vars
import {post} from '../net'
// eslint-disable-next-line no-unused-vars
import router from '../router'
// eslint-disable-next-line no-unused-vars
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
        password: '',
        password_confirm: '',
        email: '',
        type: []
      },
      rules: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        password_confirm: [
          { validator: validatePass2, trigger: 'blur' }
        ],
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
    // submit () {
    //   axios({
    //     method: 'post',
    //     url: '/api/user/account/register/',
    //     data: {
    //
    //       email: '123@qq.com',
    //       password: '123',
    //       password_confirm: '123'
    //
    //     }
    //   })
    // },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post(
            '/api/user/account/register/',
            {
              email: this.ruleForm.email,
              password: this.ruleForm.password,
              password_confirm: this.ruleForm.password_confirm
            }
            //   {
            //     headers: {
            //       'Content-Type': 'application/x-www-form-urlencoded'
            //     },
            //     withCredentials: true
            //   }).then(response => {
            //   console.log(response.data)
            // }
          )

          alert('submit!')
          Message.success('成功注册')
          this.$router.push('/')
        } else {
          Message.error('失败注册')
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
