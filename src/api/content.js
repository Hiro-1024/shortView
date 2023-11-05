import request from '@/utils/request'

// 获取视频分类
export const artGetChannelsService = () => request.get('/my/edit/list')

// 添加视频分类
export const artAddChannelService = (data) => request.post('/my/edit/add', data)

// 编辑视频分类
export const artEditChannelService = (data) => request.put('/my/put/info', data)

// 删除视频分类
export const artDelChannelService = (id) =>
  request.delete('/my/edit/del', {
    params: { id }
  })

// 获取视频列表
export const artGetListService = (params) =>
  request.get('/my/content/list', {
    params
  })

// 添加视频
// data : formData格式的对象
export const artPublishService = (data) => request.post('/my/content/add', data)

// 获取视频详情
export const artGetDetailService = (id) =>
  request.get('/my/content/info', {
    params: { id }
  })

// 编辑视频接口
export const artEditService = (data) => request.put('/my/content/info', data)