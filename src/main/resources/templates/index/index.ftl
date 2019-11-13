<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>SpringBoot在线日志</title>
	</head>
	<body>
		<h3>请 F12 打开控制台查看即时日志输出信息</h3>
		<button>点击我，生成20条日志信息</button>
	</body>
	<script type="text/javascript">
		window.onload = event => {
			document.querySelector('button').addEventListener('click', event => {
				fetch('/log', {
					method: 'GET',
				}).then(response => {
					if (response.ok){
						response.json(message => {
							// ignore
						});
					}
				});
			});
			
			
			const log = new WebSocket('ws://localhost/channel/log');
			log.onopen = event => {
				console.log('链接打开');
			}
			log.onclose = event => {
				console.log('链接关闭');
			}
			log.onerror = event => {
				console.log('链接关闭');
			}
			
			// 收到服务端的日志消息推送
			log.onmessage = event => {
				console.log("日志信息:" + event.data);
			}
		}
	</script>
</html>