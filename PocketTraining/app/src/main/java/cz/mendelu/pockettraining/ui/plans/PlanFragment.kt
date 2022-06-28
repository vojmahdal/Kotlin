package cz.mendelu.pockettraining.ui.plans

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.pockettraining.architecture.BaseFragment
import cz.mendelu.pockettraining.databinding.FragmentPlanBinding
import cz.mendelu.pockettraining.databinding.RowUsedPlanBinding
import cz.mendelu.pockettraining.model.UsedExercise
import kotlinx.coroutines.launch

class PlanFragment :
    BaseFragment<FragmentPlanBinding, PlanViewModel>(PlanViewModel::class) {
private val arguments: PlanFragmentArgs by navArgs()

    private val usedExerciseList: MutableList<UsedExercise> = mutableListOf()

    private lateinit var adapter: PlanAdapter
    private lateinit var layoutManager: LinearLayoutManager

    //diff
    inner class PlanDiffUtils(private val oldList: MutableList<UsedExercise>,
                                  private val newList: MutableList<UsedExercise>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].usedExerciseName == newList[newItemPosition].usedExerciseName
                    && oldList[oldItemPosition].pause == newList[newItemPosition].pause
                    && oldList[oldItemPosition].sets == newList[newItemPosition].sets
                    && oldList[oldItemPosition].reps == newList[newItemPosition].reps
        }
    }
    //adapter
    inner class PlanAdapter : RecyclerView.Adapter<PlanAdapter.ExerciseViewHolder>(){

        inner class ExerciseViewHolder(val binding: RowUsedPlanBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            return ExerciseViewHolder(
                RowUsedPlanBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {

            holder.binding.UsedExerciseName.text = usedExerciseList[position].usedExerciseName
            holder.binding.pause.text = usedExerciseList[position].pause.toString()
            holder.binding.reps.text = usedExerciseList[position].reps.toString()
            holder.binding.sets.text = usedExerciseList[position].sets.toString()

            holder.binding.root.setOnClickListener {
                val action = PlanFragmentDirections.actionPlanFragmentToExerciseToplanFragment()
                action.id = usedExerciseList.get(holder.adapterPosition).id!!
                action.idPlan = arguments.planId
                findNavController().navigate(action)
            }
        }
        override fun getItemCount(): Int = usedExerciseList.size
    }



    override val bindingInflater: (LayoutInflater) -> FragmentPlanBinding
        get() = FragmentPlanBinding::inflate


    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = PlanAdapter()
        binding.usedExerciseList.layoutManager = layoutManager
        binding.usedExerciseList.adapter = adapter


        viewModel.id = if(arguments.planId != -1L) arguments.planId
        else null
        if(viewModel.id == null){
            binding.deletePlanButton.visibility = View.GONE
            binding.fabNewExercise.visibility = View.GONE
            fillLayout()
        }else{
            viewModel
                .getUsedExercise()
                .observe(viewLifecycleOwner, object : Observer<MutableList<UsedExercise>>
                {
                    override fun onChanged(t: MutableList<UsedExercise>?){
                        val diffCallback = PlanDiffUtils(usedExerciseList, t!!)
                        val diffResult = DiffUtil.calculateDiff(diffCallback)
                        diffResult.dispatchUpdatesTo(adapter)
                        usedExerciseList.clear()
                        usedExerciseList.addAll(t)
                    }

                })
            lifecycleScope.launch {
                viewModel.plan = viewModel.findPlanById()
            }.invokeOnCompletion {
                fillLayout()
            }
        }
        setInteractionListeners()
    }
    private fun fillLayout(){
        if (viewModel.plan.planName.isNotEmpty()){
            binding.planName.text = viewModel.plan.planName
        }
    }
    private fun setInteractionListeners(){
        binding.planName.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.plan.planName = p0.toString()
            }

        })
        binding.fabNewExercise.setOnClickListener{
            val action = PlanFragmentDirections.actionPlanFragmentToExerciseToplanFragment()
            action.idPlan = arguments.planId
            findNavController().navigate(action)
        }
        binding.savePlanButton.setOnClickListener {
            if(binding.planName.text.isNotEmpty() &&
                binding.planName.text.length < 30){
                binding.planName.setError(null)
                lifecycleScope.launch {
                    viewModel.savePlan()
                }.invokeOnCompletion {
                    finishCurrentFragment()
                }
            }
            if(binding.planName.text.isEmpty()){
                binding.planName.setError("Cannot be empty")
            }
            if(binding.planName.text.length >= 30){
                binding.planName.setError("Text must be shorter than 30")
            }
        }
        binding.deletePlanButton.setOnClickListener {
            if (arguments.planId == -1L){
                binding.deletePlanButton.visibility = View.GONE
            }else{
                binding.deletePlanButton.visibility = View.VISIBLE
                lifecycleScope.launch{
                    viewModel.deletePlanWithExercises()
                    viewModel.deletePlan()
                }.invokeOnCompletion {
                    finishCurrentFragment()
                }}
            }
    }

    override fun onActivityCreated() {
    }

}