<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 漏洞检索
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-select v-model="query.param1" placeholder="危害程度" class="handle-select mr10">
                    <el-option key="0" label="全部" value="ALL"></el-option>
                    <el-option key="1" label="高" value="HIGH"></el-option>
                    <el-option key="2" label="中" value="MEDIUM"></el-option>
                    <el-option key="3" label="低" value="LOW"></el-option>
                </el-select>
                <el-select v-model="query.param2" placeholder="状态" class="handle-select mr10">
                    <el-option key="0" label="未知" value="0"></el-option>
                    <el-option key="1" label="已修复" value="1"></el-option>
                    <el-option key="2" label="未修复" value="2"></el-option>
                </el-select>
                <el-input style="width:300px;" v-model="query.name" placeholder="示例:CVE-2019-3494" clearable></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column type="index" align="center"></el-table-column>
                <el-table-column prop="name" label="CVE-ID" align="center"></el-table-column>
                <el-table-column prop="cwe_id" label="CWE" align="center"></el-table-column>
                <el-table-column  label="CVSS2评分" align="center">
                    <template #default="scope">
                        <el-tag :type="
                                scope.row.cvss2_nvd_base_score.includes('HIGH')
                                    ? 'danger'
                                    : scope.row.cvss2_nvd_base_score.includes('MEDIUM')
                                    ? 'warning'
                                    : scope.row.cvss2_nvd_base_score.includes('LOW')
                                    ? 'success'
                                    : ''
                            ">{{ scope.row.cvss2_nvd_base_score}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="cvss3_nvd_base_score" label="CVSS3评分" align="center">
                    <template #default="scope">
                        <el-tag :type="
                                scope.row.cvss3_nvd_base_score.includes('HigH')
                                    ? 'danger'
                                    : scope.row.cvss3_nvd_base_score.includes('MEDIUM')
                                    ? 'warning'
                                    : scope.row.cvss3_nvd_base_score.includes('LOW')
                                    ? 'info'
                                    : 'danger'
                            ">{{ scope.row.cvss3_nvd_base_score}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="状态" align="center">
                    <el-tag type="success">已修补</el-tag>
                </el-table-column>
                <el-table-column prop="name" label="Detail" align="center">
                    <template #default="scope">
                        <el-button type="primary" @click="goDetail(scope.row.name)">查看详情</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchData, getAllData, getList, getLeakByCVE } from "../api/index";

export default {
    name: "basetable",
    setup() {
        const query = reactive({
            param1: "",
            param2: "",
            param3: "",
            name: "",
            pageIndex: 1,
            pageSize: 20,
        });
        const tableData = ref([]);
        const pageTotal = ref(0);
        // 获取表格数据
        const getData = () => {
            // fetchData(query).then((res) => {
            // getList({"page":query.pageIndex}).then((res) => {
            getAllData().then((res) => {
                console.log(res)
                tableData.value = res.results
                pageTotal.value = res.count || 20
            });
        };
        getData();
        // 查询操作
        const handleSearch = () => {
            console.log(query.name)
            getLeakByCVE(query.name).then((res) => {
                console.log(res)
                let t = new Array()
                for(let i = 0; i < res.length; i ++) {
                    t.push(res[i].fields)
                    console.log(res[i])
                }
                console.log(t)
                tableData.value = t
            })
        };

        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            console.log(val)
            getData()
        };

        // 删除操作
        const handleDelete = (index) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    ElMessage.success("删除成功");
                    tableData.value.splice(index, 1);
                })
                .catch(() => {});
        };

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
            name: "",
            address: "",
        });
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            editVisible.value = true;
        };
        const saveEdit = () => {
            editVisible.value = false;
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
            });
        };

        return {
            query,
            tableData,
            pageTotal,
            editVisible,
            form,
            handleSearch,
            handlePageChange,
            handleDelete,
            handleEdit,
            saveEdit,
        };
    },
    methods: {
        goDetail(name) {
            console.log("goDetail!")
            this.$router.push({name: "Detail", params:{"name": name}})
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
