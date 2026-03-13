<template>
	<div class="main-content">
		<!-- 列表 -->
		<el-form :inline="true" :model="searchForm" class="form_content">
			<el-form-item label="通知编号">
				<el-input v-model="searchForm.tongzhibianhao" placeholder="通知编号" clearable></el-input>
			</el-form-item>
			<el-form-item label="账号">
				<el-input v-model="searchForm.zhanghao" placeholder="账号" clearable></el-input>
			</el-form-item>
			<el-form-item label="发送状态">
				<el-select v-model="searchForm.fasongzhuangtai" placeholder="发送状态" clearable>
					<el-option label="成功" value="成功"></el-option>
					<el-option label="失败" value="失败"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button class="search_btn" type="primary" @click="searchBtn">
					<i class="iconfont icon-sousuo1"></i>
					搜索
				</el-button>
				<el-button class="reset_btn" @click="resetForm">
					<i="iconfont icon-zhongzhi1"></i>
					重置
				</el-button>
			</el-form-item>
		</el-form>
		<div class="table_content">
			<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" class="table">
				<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
				<el-table-column prop="tongzhibianhao" header-align="center" align="center" label="通知编号" min-width="150"></el-table-column>
				<el-table-column prop="zhanghao" header-align="center" align="center" label="账号" min-width="120"></el-table-column>
				<el-table-column prop="shouji" header-align="center" align="center" label="手机" min-width="120"></el-table-column>
				<el-table-column prop="tongzhineirong" header-align="center" align="center" label="通知内容" min-width="200" show-overflow-tooltip></el-table-column>
				<el-table-column prop="fasongshijian" header-align="center" align="center" label="发送时间" min-width="160"></el-table-column>
				<el-table-column prop="fasongzhuangtai" header-align="center" align="center" label="发送状态" min-width="100">
					<template #default="scope">
						<el-tag v-if="scope.row.fasongzhuangtai === '成功'" type="success">{{scope.row.fasongzhuangtai}}</el-tag>
						<el-tag v-else type="danger">{{scope.row.fasongzhuangtai}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="shibaiyuanyin" header-align="center" align="center" label="失败原因" min-width="200" show-overflow-tooltip></el-table-column>
				<el-table-column prop="chongcishu" header-align="center" align="center" label="重试次数" min-width="80"></el-table-column>
			</el-table>
			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				:page-sizes="[10, 20, 50, 100]"
				:page-size="pageSize"
				:total="totalPage"
				layout="total, sizes, prev, pager, next, jumper"
				class="pagination_content"
			></el-pagination>
		</div>
	</div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { getCurrentInstance } from 'vue'

const { proxy } = getCurrentInstance()

const searchForm = ref({
    tongzhibianhao: '',
    zhanghao: '',
    fasongzhuangtai: ''
})

const dataList = ref([])
const pageIndex = ref(1)
const pageSize = ref(10)
const totalPage = ref(0)
const dataListLoading = ref(false)

const getDataList = () => {
    dataListLoading.value = true
    const params = {
        page: pageIndex.value,
        limit: pageSize.value,
        tongzhibianhao: searchForm.value.tongzhibianhao,
        zhanghao: searchForm.value.zhanghao,
        fasongzhuangtai: searchForm.value.fasongzhuangtai
    }
    proxy.$http({
        url: 'tongzhiRecord/page',
        method: 'get',
        params: params
    }).then(res => {
        if (res.data && res.data.code === 0) {
            dataList.value = res.data.data.list
            totalPage.value = res.data.data.total
        } else {
            ElMessage.error(res.data.msg)
        }
        dataListLoading.value = false
    })
}

const searchBtn = () => {
    pageIndex.value = 1
    getDataList()
}

const resetForm = () => {
    searchForm.value = {
        tongzhibianhao: '',
        zhanghao: '',
        fasongzhuangtai: ''
    }
    pageIndex.value = 1
    getDataList()
}

const sizeChangeHandle = (val) => {
    pageSize.value = val
    pageIndex.value = 1
    getDataList()
}

const currentChangeHandle = (val) => {
    pageIndex.value = val
    getDataList()
}

const selectionChangeHandle = (val) => {
}

getDataList()
</script>

<style lang="scss" scoped>
.main-content {
    padding: 20px;
}

.form_content {
    background: #fff;
    padding: 20px;
    border-radius: 4px;
    margin-bottom: 20px;
}

.table_content {
    background: #fff;
    padding: 20px;
    border-radius: 4px;
}

.table {
    width: 100%;
}

.pagination_content {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
}

.search_btn {
    background: #409eff;
    border-color: #409eff;
}

.reset_btn {
    background: #909399;
    border-color: #909399;
    color: #fff;
}
</style>
