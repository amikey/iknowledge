package cn.xdf.learn.entity;

public class Words {

	private Integer id;
	private String word; 		//单词
	private String meaning;		//词性和词义
	private String root;		//词根
	private String rootMeaning;	//词根含义
	private String handoutPage;	//讲义页码
	private String pronunciation;//美音音标
	private String maleVoice;	//美音男声
	
	
	public Words(Integer id,String word, String meaning, String root, String rootMeaning,
			String handoutPage, String pronunciation, String maleVoice) {
		super();
		this.id = id;
		this.word = word;
		this.meaning = meaning;
		this.root = root;
		this.rootMeaning = rootMeaning;
		this.handoutPage = handoutPage;
		this.pronunciation = pronunciation;
		this.maleVoice = maleVoice;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Words() {
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getRootMeaning() {
		return rootMeaning;
	}
	public void setRootMeaning(String rootMeaning) {
		this.rootMeaning = rootMeaning;
	}
	public String getHandoutPage() {
		return handoutPage;
	}
	public void setHandoutPage(String handoutPage) {
		this.handoutPage = handoutPage;
	}
	public String getPronunciation() {
		return pronunciation;
	}
	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}
	public String getMaleVoice() {
		return maleVoice;
	}
	public void setMaleVoice(String maleVoice) {
		this.maleVoice = maleVoice;
	}
	
}
