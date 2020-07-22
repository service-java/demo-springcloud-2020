# 相当于定义java包
namespace java com.spring.cloud.chapter19.thrift.pojo
# 定义UserPojo数据结构
struct UserPojo {
    1:i64 id
    2:string userName
    3:string note
}
# 定义RolePojo数据结构
struct RolePojo {
    1:i64 id
    2:string roleName
    3:string note
}

# 定义用户服务结构
service UserService {
    UserPojo getUser(1:i64 id)
}

# 定义角色服务接口
service RoleService {
    list<RolePojo> getRoleByUserId(1: i64 userId)
}