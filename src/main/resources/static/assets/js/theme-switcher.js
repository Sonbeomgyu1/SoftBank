(() => {
  'use strict'

  // 기본적으로 'light' 테마를 설정
  const getPreferredTheme = () => 'light'

  // 페이지에 적용할 테마를 설정하는 함수
  const setTheme = theme => {
    document.documentElement.setAttribute('data-bs-theme', theme)
  }

  // 페이지 로드 시 초기화
  window.addEventListener('DOMContentLoaded', () => {
    // 페이지 로드 시 기본 'light' 테마로 설정
    setTheme(getPreferredTheme())

    // 로컬 스토리지에 저장된 다크 테마 선택 여부를 확인하고, 선택된 경우에만 다크 테마를 적용
    const storedTheme = sessionStorage.getItem('theme')
    if (storedTheme === 'dark') {
      setTheme('dark')
    }

    // 테마 상태에 맞게 토글 버튼 업데이트
    showActiveTheme(storedTheme || getPreferredTheme())

    // 테마 토글 버튼에 클릭 이벤트 리스너 추가
    document.querySelectorAll('[data-bs-toggle="mode"]')
      .forEach(toggle => {
        toggle.addEventListener('click', () => {
          // 체크박스 상태에 따라 테마를 'dark' 또는 'light'로 설정
          const theme = toggle.querySelector('input[type="checkbox"]').checked ? 'dark' : 'light'

          // 선택한 테마를 문서에 적용
          setTheme(theme)

          // 세션에 테마 정보 저장 (페이지 이동 간에만 유지)
          sessionStorage.setItem('theme', theme)

          // 토글 버튼 상태를 현재 테마에 맞게 업데이트
          showActiveTheme(theme)
        })
      })
  })

  // 현재 활성화된 테마에 맞게 토글 버튼을 업데이트하는 함수
  const showActiveTheme = (theme) => {
    const themeSwitcher = document.querySelector('[data-bs-toggle="mode"]')

    if (!themeSwitcher) {
      return
    }

    // 체크박스를 찾아서 현재 테마에 맞게 체크 상태를 변경
    const themeSwitcherCheck = themeSwitcher.querySelector('input[type="checkbox"]')

    if (theme === 'dark') {
      themeSwitcherCheck.checked = true  // 다크 테마일 경우 체크박스를 체크
    } else {
      themeSwitcherCheck.checked = false  // 라이트 테마일 경우 체크박스를 해제
    }
  }

  // 시스템의 색상 모드가 변경될 때마다 테마를 업데이트
  window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', () => {
    // 시스템 색상 모드가 변경되면, 'light' 테마를 적용
    setTheme(getPreferredTheme())
  })
})()
