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
            <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
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
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        pass: '',
        checkPass: '',
        email: '',
        type: []
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
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
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
          this.$router.push('/')
        } else {
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
